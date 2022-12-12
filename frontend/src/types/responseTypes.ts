import { Course, CourseRequest, PastEvaluation, PastEvaluationItem, PreApprovalForm, PreviousCourseRequest, SchoolAssociatedCourse, StudentAssociatedCourse, StudentAssociatedWishlist, UniversityDetailed, UniversityProxy, User } from ".";

export type ResponseUser = Record<'data', User>

export type ResponseStudentCourseWishlist = Record<'data', Array<StudentAssociatedWishlist>>

export type ResponseAllStudentCourseWishlist = Record<'data', Array<StudentAssociatedCourse>>

export type ResponseAllSubmitedPreApprovals = Record<'data', Array<PreApprovalForm>>

export type ResponseApprovePreApproval = Record<'data', PreApprovalForm>

export type ResponseCourseList = Record<'data', Array<Course>>

export type ResponseCourse = Record<'data', Course>

export type ResponseSchoolSpecificCourses = Record<'data', Array<SchoolAssociatedCourse>>

export type ResponseStudentSpecificCourseWishlist = Record<'data', StudentAssociatedWishlist>

export type ResponsePreviousCourseRequests = Record<'data', Array<PreviousCourseRequest>>

export type ResponseCourseRequest = Record<'data', CourseRequest>

export type ResponsePreApprovalForm = Record<'data', PreApprovalForm>

export type ResponsePreApprovalFormList = Record<'data', Array<PreApprovalForm>>

export type ResponseUniversities = Record<'data', Array<UniversityProxy>>

export type ResponseUniversity = Record<'data', UniversityDetailed>

export type ResponseEvaluation = Record <'data', PastEvaluation>