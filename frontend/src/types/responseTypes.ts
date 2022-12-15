
import { BilkentCourse, Course, CourseRequest, LearningAgreement, PastEvaluation, PastEvaluationItem, PreApprovalForm, PreviousCourseRequest, Student, StudentAssociatedCourse, StudentAssociatedPastEvaluationItem, StudentAssociatedWishlist, UniAssociatedCourse, UniversityDetailed, UniversityProxy, User } from ".";

interface Response<T> {
    data: T,
    message: string,
}

export type ResponseUser = Response<User>

export type ResponseStudentCourseWishlist = Response<Array<StudentAssociatedWishlist>>

export type ResponseAllStudentCourseWishlist = Response<Array<StudentAssociatedCourse>>

export type ResponseAllSubmitedPreApprovals = Response<Array<PreApprovalForm>>

export type ResponseApprovePreApproval = Response<PreApprovalForm>

export type ResponseCourseList = Response<Array<BilkentCourse>>

export type ResponseCourse = Response<Course>

export type ResponseUniSpecificCourses = Response<Array<UniAssociatedCourse>>

export type ResponseStudentSpecificCourseWishlist = Response<StudentAssociatedWishlist>

export type ResponsePreviousCourseRequests = Response<Array<PreviousCourseRequest>>

export type ResponseCourseRequest = Response<CourseRequest>

export type ResponsePreApprovalForm = Response<PreApprovalForm>

export type ResponsePreApprovalFormList = Response<Array<PreApprovalForm>>

export type ResponseUniversities = Response<Array<UniversityProxy>>

export type ResponseUniversity = Response<UniversityDetailed>

export type ResponseStudentPastEvaluation = Response<StudentAssociatedPastEvaluationItem>

export type ResponseAllLearningAgreements = Response<Array<LearningAgreement>>

export type ResponseStudent = Response<Student>